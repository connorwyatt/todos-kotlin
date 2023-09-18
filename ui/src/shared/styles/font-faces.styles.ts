import { fontFace } from "polished"
import { FontFaceConfiguration } from "polished/lib/types/fontFaceConfiguration"
import { css } from "styled-components"

import { FontStyle, FontWeight, weightToName } from "~/shared/styles/utilities/font-face-utilities"

const clearSansWeights: { weight: FontWeight; styles: FontStyle[] }[] = [
    { weight: 100, styles: ["normal"] },
    { weight: 300, styles: ["normal"] },
    { weight: 400, styles: ["normal", "italic"] },
    { weight: 500, styles: ["normal", "italic"] },
    { weight: 700, styles: ["normal", "italic"] },
]

export const fontFaces = css`
    ${clearSansWeights.flatMap(({ weight, styles }) => {
        const fontFaceBase: Pick<FontFaceConfiguration, "fontFamily" | "fontDisplay" | "fileFormats"> = {
            fontFamily: "Clear Sans",
            fontDisplay: "block",
            fileFormats: ["woff"],
        }

        const normalFontFilePath = `/fonts/clear-sans/clear-sans-${weightToName(weight)}`

        return styles.map((style) =>
            fontFace({
                ...fontFaceBase,
                fontWeight: `${weight}`,
                fontStyle: style,
                fontFilePath: style === "normal" ? normalFontFilePath : `${normalFontFilePath}-${style}`,
            }),
        )
    })};
`
