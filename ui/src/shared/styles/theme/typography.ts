import { transparentize } from "polished"

import { palette } from "~/shared/styles/palette"
import { Typography, TypographyStyleVariant } from "~/shared/styles/theme/types/typography"

const textColor = palette.jet

const paragraphStyle: TypographyStyleVariant = {
    normal: {
        fontFamily: '"Nunito Sans", sans-serif',
        fontSize: "1rem",
        fontWeight: 400,
        lineHeight: 1.25,
    },
    overrides: {
        emphasized: {
            fontWeight: 700,
        },
    },
}

export const typography: Typography = {
    colors: {
        primary: textColor,
        secondary: transparentize(0.5, textColor),
        disabled: transparentize(0.8, textColor),
    },
    styles: {
        paragraph: paragraphStyle,
        paragraphSubtext: {
            ...paragraphStyle,
            normal: {
                ...paragraphStyle.normal,
                fontSize: "0.75rem",
                fontWeight: 500,
            },
            overrides: {
                ...paragraphStyle.overrides,
                emphasized: { ...paragraphStyle.overrides.emphasized, fontWeight: 800 },
            },
        },
    },
}
