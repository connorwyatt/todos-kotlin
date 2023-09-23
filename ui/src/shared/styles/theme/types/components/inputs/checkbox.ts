export interface Checkbox {
    states: {
        unchecked: CheckboxState
        checked: CheckboxState
    }
    sizes: {
        medium: string
        large: string
    }
}

export interface CheckboxState {
    normal: CheckboxElementState
    focus: CheckboxElementState
}

export interface CheckboxElementState {
    backgroundColor: string
    borderColor: string
    tickColor: string
}
