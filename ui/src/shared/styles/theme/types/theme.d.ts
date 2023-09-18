import "styled-components"
import { App } from "~/shared/styles/theme/types/app"
import { Typography } from "~/shared/styles/theme/types/typography"

declare module "styled-components" {
    export interface DefaultTheme {
        app: App
        typography: Typography
    }
}
