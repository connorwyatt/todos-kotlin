import { FC, PropsWithChildren } from "react"
import { DefaultTheme } from "styled-components"

import { Container } from "~/shared/components/layout/stack.styles"

interface BaseStackProps extends PropsWithChildren {
    direction: "horizontal" | "vertical"
    spacing?: keyof DefaultTheme["spacing"]
}

const BaseStack: FC<BaseStackProps> = ({ children, direction, spacing = null }) => {
    return (
        <Container $direction={direction} $spacing={spacing}>
            {children}
        </Container>
    )
}

export type StackProps = Omit<BaseStackProps, "direction">

const Horizontal: FC<StackProps> = (props) => {
    return <BaseStack {...props} direction="horizontal" />
}

const Vertical: FC<StackProps> = (props) => {
    return <BaseStack {...props} direction="vertical" />
}

export const Stack = { Horizontal, Vertical }
