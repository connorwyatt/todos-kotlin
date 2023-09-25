import { ChangeEvent, FC, useCallback } from "react"
import { DefaultTheme } from "styled-components"

import { Icon } from "~/shared/components/icons/icons"
import { Container, FakeInput, FakeInputTick, Input } from "~/shared/components/inputs/checkbox.styles"

export interface CheckboxProps {
    displayOnly?: boolean
    onChange?: (newValue: boolean) => void
    size?: keyof DefaultTheme["components"]["inputs"]["checkbox"]["sizes"]
    value: boolean
}

export const Checkbox: FC<CheckboxProps> = ({ displayOnly = false, onChange, size = "medium", value }) => {
    const handleChange = useCallback(
        (event: ChangeEvent<HTMLInputElement>) => {
            onChange?.(event.target.checked)
        },
        [onChange],
    )

    return (
        <Container $size={size}>
            <Input
                type="checkbox"
                checked={value}
                onChange={handleChange}
                disabled={displayOnly}
                $displayOnly={displayOnly}
            />

            <FakeInput $checked={value} $displayOnly={displayOnly} />

            <FakeInputTick $checked={value} $displayOnly={displayOnly}>
                <Icon name="checkmark" size="variable" />
            </FakeInputTick>
        </Container>
    )
}
