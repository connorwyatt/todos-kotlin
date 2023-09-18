import React from "react"
import ReactDOM from "react-dom/client"
import { ThemeProvider } from "styled-components"

import { App } from "~/app"
import { ReactQueryClientProvider } from "~/shared/extensions/react-query/react-query-client-provider"
import { GlobalStyles } from "~/shared/styles/global.styles"
import { theme } from "~/shared/styles/theme/theme"

const rootElement = document.getElementById("root")!

ReactDOM.createRoot(rootElement).render(
    <React.StrictMode>
        <ReactQueryClientProvider>
            <ThemeProvider theme={theme}>
                <GlobalStyles />

                <App />
            </ThemeProvider>
        </ReactQueryClientProvider>
    </React.StrictMode>,
)
