import { QueryClientProvider } from "@tanstack/react-query"
import { FC, PropsWithChildren } from "react"

import { queryClient } from "~/shared/extensions/react-query/query-client"

export const ReactQueryClientProvider: FC<PropsWithChildren> = ({ children }) => {
    return <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
}
