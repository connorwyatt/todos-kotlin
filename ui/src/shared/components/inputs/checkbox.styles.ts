import styled, { css, DefaultTheme } from "styled-components"

import { forceSize } from "~/shared/styles/utilities/force-size"

export interface ContainerProps {
    $size: keyof DefaultTheme["components"]["inputs"]["checkbox"]["sizes"]
}

export const Container = styled.div<ContainerProps>(
    ({ theme, $size }) => css`
        ${forceSize(theme.components.inputs.checkbox.sizes[$size])};
        position: relative;
    `,
)

export interface InputProps {
    $displayOnly: boolean
}

export const Input = styled.input<InputProps>(
    ({ $displayOnly }) => css`
        appearance: none;
        cursor: pointer;
        inset: 0;
        position: absolute;

        ${$displayOnly &&
        css`
            cursor: default;
        `};
    `,
)

export interface FakeInputProps {
    $checked: boolean
    $displayOnly: boolean
}

export const FakeInput = styled.div<FakeInputProps>(({ theme, $checked, $displayOnly }) => {
    const checkbox = theme.components.inputs.checkbox

    return css`
        background-color: ${$checked
            ? checkbox.states.checked.normal.backgroundColor
            : checkbox.states.unchecked.normal.backgroundColor};
        border: 1px solid
            ${$checked ? checkbox.states.checked.normal.borderColor : checkbox.states.unchecked.normal.borderColor};
        border-radius: 25%;
        inset: 0;
        pointer-events: none;
        position: absolute;
        transition:
            background-color ease-in-out 0.2s,
            border-color ease-in-out 0.2s;

        ${!$displayOnly &&
        css`
            ${Input}:hover ~ &,
            ${Input}:focus ~ &,
            ${Input}:active ~ & {
                background-color: ${$checked
                    ? checkbox.states.checked.focus.backgroundColor
                    : checkbox.states.unchecked.focus.backgroundColor};
                border-color: ${$checked
                    ? checkbox.states.checked.focus.borderColor
                    : checkbox.states.unchecked.focus.borderColor};
            }
        `};
    `
})

export interface FakeInputTickProps {
    $checked: boolean
    $displayOnly: boolean
}

export const FakeInputTick = styled.div<FakeInputTickProps>(({ theme, $checked, $displayOnly }) => {
    const checkbox = theme.components.inputs.checkbox

    return css`
        color: ${$checked ? checkbox.states.checked.normal.tickColor : checkbox.states.unchecked.normal.tickColor};
        inset: 10%;
        pointer-events: none;
        position: absolute;
        transform: scale(${$checked ? 1 : 0});
        transition:
            color ease-in-out 0.2s,
            transform ease-in-out 0.2s;

        ${!$displayOnly &&
        css`
            ${Input}:hover ~ &,
            ${Input}:focus ~ &,
            ${Input}:active ~ & {
                color: ${$checked
                    ? checkbox.states.checked.focus.tickColor
                    : checkbox.states.unchecked.focus.tickColor};
            }
        `};
    `
})
