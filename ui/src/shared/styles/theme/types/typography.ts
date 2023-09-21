export interface TypographyStyleOverrides {
    emphasized: Pick<TypographyStyle, "fontWeight"> | null
}

export interface TypographyStyle {
    fontFamily: string
    fontSize: string
    fontWeight: number
    lineHeight: number
}

export interface TypographyStyleVariant {
    normal: TypographyStyle
    overrides: TypographyStyleOverrides
}

export interface Typography {
    colors: {
        primary: string
        secondary: string
        disabled: string
    }
    styles: {
        paragraph: TypographyStyleVariant
        paragraphSubtext: TypographyStyleVariant
    }
}
