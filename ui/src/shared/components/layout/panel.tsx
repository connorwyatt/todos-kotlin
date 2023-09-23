import { FC, PropsWithChildren } from "react"

import { Container } from "~/shared/components/layout/panel.styles"

export interface PanelProps extends PropsWithChildren {
    removePadding?: boolean
}

export const Panel: FC<PanelProps> = ({ children, removePadding = false }) => {
    return <Container $removePadding={removePadding}>{children}</Container>
}
