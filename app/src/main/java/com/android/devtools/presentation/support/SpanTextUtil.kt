package com.android.devtools.presentation.support

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

class SpanTextUtil {

    private val jsonKeyColor: Color = Color.Red
    private val jsonValueColor: Color = Color.Blue
    private val jsonDigitsAndNullValueColor: Color = Color.Green
    private val jsonSignElementsColor: Color = Color.Cyan
    private val jsonBooleanColor: Color = Color.Yellow

    private companion object {
        // corresponds to length of word 'true'
        private const val BOOLEAN_TRUE_INDEX_OFFSET = 4

        // corresponds to length of word 'false'
        private const val BOOLEAN_FALSE_INDEX_OFFSET = 5
    }

    private enum class TokenType(val delimiters: Set<String>) {
        STRING(setOf("\"")),
        ARRAY(setOf("[", "]")),
        OBJECT(setOf("{", "}")),
        KEY_SEPARATOR(setOf(":")),
        VALUE_SEPARATOR(setOf(",")),
        BOOLEAN(setOf("true", "false")),
        NONE(setOf()),
        ;

        companion object {
            val allPossibleTokens = entries.map { it.delimiters }.flatten().toSet()
        }
    }

    private fun CharSequence.indexOfNextToken(startIndex: Int = 0): Pair<Int, TokenType> {
        val (index, matched) =
            findAnyOf(
                strings = TokenType.allPossibleTokens,
                startIndex = startIndex,
                ignoreCase = true,
            ) ?: return -1 to TokenType.NONE
        val tokenType =
            when (matched) {
                in TokenType.ARRAY.delimiters -> TokenType.ARRAY
                in TokenType.OBJECT.delimiters -> TokenType.OBJECT
                in TokenType.KEY_SEPARATOR.delimiters -> TokenType.KEY_SEPARATOR
                in TokenType.VALUE_SEPARATOR.delimiters -> TokenType.VALUE_SEPARATOR
                in TokenType.STRING.delimiters -> SpanTextUtil.TokenType.STRING
                in TokenType.BOOLEAN.delimiters -> SpanTextUtil.TokenType.BOOLEAN
                else -> null
            }
        tokenType?.let {
            return index to it
        }
        return -1 to TokenType.NONE
    }

    private fun CharSequence.indexOfNextUnescapedQuote(startIndex: Int = 0): Int {
        var index = indexOf('"', startIndex)
        while (index < length) {
            if (this[index] == '"' && (index == 0 || this[index - 1] != '\\')) {
                return index
            }
            index = indexOf('"', index + 1)
        }
        return -1
    }

    fun spanJson(prettyPrintedInput: String): AnnotatedString {
        var lastTokenType: TokenType? = null
        var index = 0

        return buildAnnotatedString {
            append(prettyPrintedInput)

            addStyle(
                style = SpanStyle(color = jsonDigitsAndNullValueColor),
                start = 0,
                end = prettyPrintedInput.length
            )

            while (index < prettyPrintedInput.length) {
                val (tokenIndex, tokenType) = prettyPrintedInput.indexOfNextToken(startIndex = index)

                when (tokenType) {
                    TokenType.BOOLEAN -> {
                        toAnnotatedString()
                            .setBooleanColor(tokenIndex)
                            .also { endIndex ->
                                index = endIndex
                                addStyle(
                                    style = SpanStyle(color = jsonBooleanColor),
                                    start = tokenIndex,
                                    end = index
                                )
                            }
                    }

                    TokenType.ARRAY,
                    TokenType.OBJECT,
                    TokenType.KEY_SEPARATOR,
                    TokenType.VALUE_SEPARATOR,
                    -> {
                        addStyle(
                            style = SpanStyle(color = jsonSignElementsColor),
                            start = tokenIndex,
                            end = tokenIndex + 1
                        )
                        index = tokenIndex + 1
                    }

                    TokenType.STRING -> {
                        toAnnotatedString()
                            .setStringColor(tokenIndex)
                            ?.also { endIndex ->
                                val color = when (lastTokenType) {
                                    TokenType.ARRAY,
                                    TokenType.OBJECT,
                                    TokenType.VALUE_SEPARATOR,
                                    TokenType.NONE,
                                    null,
                                    -> jsonKeyColor

                                    else -> jsonValueColor
                                }

                                index = endIndex + 1
                                addStyle(
                                    style = SpanStyle(color = color),
                                    start = tokenIndex,
                                    end = index
                                )
                            } ?: toAnnotatedString()
                    }
                    TokenType.NONE -> toAnnotatedString()
                }
                lastTokenType = tokenType
            }
        }
    }

    private fun AnnotatedString.setBooleanColor(tokenIndex: Int): Int {
        val endIndex =
            if (this[tokenIndex].equals('t', ignoreCase = true)) {
                tokenIndex + BOOLEAN_TRUE_INDEX_OFFSET
            } else {
                tokenIndex + BOOLEAN_FALSE_INDEX_OFFSET
            }

        return endIndex
    }

    private fun AnnotatedString.setStringColor(tokenIndex: Int, ): Int? {
        @Suppress("TooGenericExceptionCaught", "SwallowedException")
        val endIndex =
            try {
                this.indexOfNextUnescapedQuote(tokenIndex + 1)
            } catch (e: Exception) {
                -1
            }
        // if we somehow get an incomplete string, we lose the ability to parse any other
        // tokens, so just return now
        if (endIndex < tokenIndex) return null

        return endIndex
    }
}