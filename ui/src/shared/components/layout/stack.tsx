import { FC, PropsWithChildren } from "react"
import { DefaultTheme } from "styled-components"

import { Container, ContainerProps } from "~/shared/components/layout/stack.styles"

interface BaseStackProps extends PropsWithChildren {
    align?: ContainerProps["$align"]
    direction: ContainerProps["$direction"]
    justify?: ContainerProps["$justify"]
    spacing?: keyof DefaultTheme["spacing"]
}

const BaseStack: FC<BaseStackProps> = ({
    children,
    align = "stretch",
    direction,
    justify = "stretch",
    spacing = null,
}) => {
    return (
        <Container $align={align} $direction={direction} $justify={justify} $spacing={spacing}>
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
