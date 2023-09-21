import styled, { css, DefaultTheme } from "styled-components"

import { typographyStyleCss } from "~/shared/styles/utilities/typography-style-css"

export interface StyledTextProps {
    $color: keyof DefaultTheme["typography"]["colors"]
    $emphasized: boolean
    $italicized: boolean
    $style: keyof DefaultTheme["typography"]["styles"]
}

export const StyledText = styled.div<StyledTextProps>(({ theme, $color, $emphasized, $italicized, $style }) => {
    const style = theme.typography.styles[$style]

    return css`
        ${typographyStyleCss(style.normal)};
        ${$emphasized && style.overrides.emphasized != null && typographyStyleCss(style.overrides.emphasized)};
        color: ${theme.typography.colors[$color]};
        font-style: ${$italicized ? "italic" : null};
    `
})
