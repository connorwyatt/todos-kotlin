import { transparentize } from "polished"
import { DefaultTheme } from "styled-components"

import { palette } from "~/shared/styles/palette"
import { typography } from "~/shared/styles/theme/typography"

export const theme: DefaultTheme = {
    app: {
        backgroundColors: [palette.jet, palette.paynesGray],
        textColor: typography.colors.primary,
    },
    components: {
        icons: {
            sizes: {
                medium: "1rem",
            },
        },
        inputs: {
            checkbox: {
                states: {
                    checked: {
                        normal: {
                            backgroundColor: palette.transparent,
                            borderColor: palette.onyx,
                            tickColor: palette.biceBlue,
                        },
                        focus: {
                            backgroundColor: transparentize(0.9, palette.argentinianBlue),
                            borderColor: palette.biceBlue,
                            tickColor: palette.biceBlue,
                        },
                    },
                    unchecked: {
                        normal: {
                            backgroundColor: palette.transparent,
                            borderColor: palette.onyx,
                            tickColor: palette.transparent,
                        },
                        focus: {
                            backgroundColor: transparentize(0.9, palette.argentinianBlue),
                            borderColor: palette.biceBlue,
                            tickColor: palette.transparent,
                        },
                    },
                },
                sizes: {
                    medium: "1rem",
                    large: "1.5rem",
                },
            },
        },
        panel: {
            backgroundColor: transparentize(0.25, palette.antiflashWhite),
            borderColor: palette.antiflashWhite,
            shadowColor: transparentize(0.9, palette.jet),
        },
    },
    spacing: {
        xsmall: "0.25rem",
        small: "0.5rem",
        medium: "1rem",
    },
    typography,
}
