import { linearGradient, normalize } from "polished"
import { createGlobalStyle, css } from "styled-components"

import { fontFaces } from "~/shared/styles/font-faces.styles"

export const GlobalStyles = createGlobalStyle(
    ({ theme }) => css`
        ${normalize()};
        ${fontFaces};

        :root {
            font-size: 14px; /* stylelint-disable-line unit-allowed-list */
            -webkit-font-smoothing: antialiased;
            -moz-osx-font-smoothing: grayscale;
            font-synthesis: none;
            text-rendering: optimizelegibility;
        }

        * {
            box-sizing: border-box;
        }

        body {
            background-color: ${linearGradient({
                colorStops: theme.app.backgroundColors,
                toDirection: "to top right",
                fallback: theme.app.backgroundColors[0],
            })};
            background-size: cover;
            color: ${theme.app.textColor};
            font-family: ${theme.typography.fontFamilies.body};
            min-height: 100vh; /* stylelint-disable-line unit-allowed-list */
            padding: 1rem;
        }
    `,
)
