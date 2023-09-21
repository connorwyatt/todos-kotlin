import { fluidRange, linearGradient, normalize } from "polished"
import { createGlobalStyle, css } from "styled-components"

import { typographyStyleCss } from "~/shared/styles/utilities/typography-style-css"

export const GlobalStyles = createGlobalStyle(
    ({ theme }) => css`
        ${normalize()};

        :root {
            ${fluidRange(
                {
                    prop: "fontSize",
                    fromSize: "14px",
                    toSize: "16px",
                },
                "640px",
                "1280px",
            )};
            -webkit-font-smoothing: antialiased;
            -moz-osx-font-smoothing: grayscale;
            font-synthesis: none;
            text-rendering: optimizelegibility;
        }

        * {
            box-sizing: border-box;
        }

        body {
            ${linearGradient({
                colorStops: theme.app.backgroundColors,
                toDirection: "to top right",
                fallback: theme.app.backgroundColors[0],
            })};
            ${typographyStyleCss(theme.typography.styles.paragraph.normal)};
            background-size: cover;
            color: ${theme.app.textColor};
        }
    `,
)
