import { ComponentType, FC, JSX, PropsWithChildren, useContext, useMemo } from "react"

import { TypographyContext, TypographyContextValue } from "~/shared/components/typography/typography-context"
import { StyledText } from "~/shared/components/typography/typography.styles"

export type TextStyleProps = Pick<TypographyContextValue, "textStyle"> &
    Partial<Pick<TypographyContextValue, "color" | "emphasized" | "italicized">> & {
        as?: keyof JSX.IntrinsicElements | ComponentType
    } & PropsWithChildren

const TextStyle: FC<TextStyleProps> = ({
    children,
    as,
    color = "primary",
    emphasized = false,
    italicized = false,
    textStyle,
}) => {
    const value: TypographyContextValue = useMemo(
        () => ({ color, emphasized, italicized, textStyle }),
        [color, emphasized, italicized, textStyle],
    )

    return (
        <TypographyContext.Provider value={value}>
            <TypographyContextStyledText as={as}>{children}</TypographyContextStyledText>
        </TypographyContext.Provider>
    )
}

export const Paragraph: FC<Omit<TextStyleProps, "textStyle">> = (props) => (
    <TextStyle {...props} textStyle="paragraph" />
)

export const ParagraphSubtext: FC<Omit<TextStyleProps, "textStyle">> = (props) => (
    <TextStyle {...props} textStyle="paragraphSubtext" />
)

interface TypographyContextStyledTextProps extends PropsWithChildren {
    as: keyof JSX.IntrinsicElements | ComponentType | undefined
}

const TypographyContextStyledText: FC<TypographyContextStyledTextProps> = ({ children, as }) => {
    const { color, emphasized, italicized, textStyle } = useContext(TypographyContext)
    return (
        <StyledText as={as} $color={color} $emphasized={emphasized} $italicized={italicized} $style={textStyle}>
            {children}
        </StyledText>
    )
}
