import { css } from "styled-components"

import { TypographyStyle } from "~/shared/styles/theme/types/typography"

export const typographyStyleCss = ({ fontWeight, ...rest }: Partial<TypographyStyle>) => css`
    ${rest};
    font-weight: ${fontWeight};
`
