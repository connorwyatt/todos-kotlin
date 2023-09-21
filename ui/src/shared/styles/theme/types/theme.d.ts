import "styled-components"
import { App } from "~/shared/styles/theme/types/app"
import { Components } from "~/shared/styles/theme/types/components"
import { Spacing } from "~/shared/styles/theme/types/spacing"
import { Typography } from "~/shared/styles/theme/types/typography"

declare module "styled-components" {
    export interface DefaultTheme {
        app: App
        components: Components
        spacing: Spacing
        typography: Typography
    }
}
