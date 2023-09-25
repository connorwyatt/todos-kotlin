import { transparentize } from "polished"

import { palette } from "~/shared/styles/palette"
import { Typography } from "~/shared/styles/theme/types/typography"

export const invertedTypographyColors: Typography["colors"] = {
    primary: palette.antiflashWhite,
    secondary: transparentize(0.5, palette.antiflashWhite),
    disabled: transparentize(0.8, palette.antiflashWhite),
}
