import React from "react"
import ReactDOM from "react-dom/client"

import { App } from "~/app"
import { ReactQueryClientProvider } from "~/shared/extensions/react-query/react-query-client-provider"
import { GlobalStyles } from "~/shared/styles/global.styles"

const rootElement = document.getElementById("root")!

ReactDOM.createRoot(rootElement).render(
    <React.StrictMode>
        <GlobalStyles />

        <ReactQueryClientProvider>
            <App />
        </ReactQueryClientProvider>
    </React.StrictMode>,
)
