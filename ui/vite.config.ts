import * as path from "node:path"

import react from "@vitejs/plugin-react"
import { defineConfig } from "vite"
import svgr from "vite-plugin-svgr"

export default defineConfig({
    plugins: [react(), svgr()],
    resolve: {
        alias: {
            "~": path.resolve(__dirname, "./src"),
        },
    },
    server: {
        proxy: {
            "/api": {
                target: "http://127.0.0.1:8080",
                rewrite: (path: string) => path.replace("/api", ""),
            },
        },
    },
})
