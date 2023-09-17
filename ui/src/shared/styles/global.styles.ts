import { createGlobalStyle } from "styled-components"

export const GlobalStyles = createGlobalStyle`
    :root {
        font-size: 14px; /* stylelint-disable-line unit-allowed-list */
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
        font-synthesis: none;
        text-rendering: optimizelegibility;
        text-size-adjust: 100%;
    }

    body {
        background-color: hsl(204deg 20% 98%);
        font-family: system-ui, sans-serif;
    }

    pre {
        font-family: ui-monospace, 'Cascadia Code', 'Source Code Pro', Menlo, Consolas, 'DejaVu Sans Mono', monospace;
    }
`
