import styled, { css, DefaultTheme } from "styled-components"

import { padding } from "~/shared/styles/utilities/padding"

export interface ContainerProps {
    $spacing: keyof DefaultTheme["spacing"]
}

export const Container = styled.div<ContainerProps>(
    ({ $spacing }) => css`
        ${padding($spacing)};
    `,
)
