import { FC } from "react"

import { icons } from "~/shared/components/icons/internal/icons"
import { IconSize } from "~/shared/components/icons/internal/types"

export interface IconProps {
    inline?: boolean
    name: keyof typeof icons
    size?: IconSize
}

export const Icon: FC<IconProps> = ({ inline = false, name, size = "medium" }) => {
    const Icon = icons[name]
    return <Icon $inline={inline} $size={size} />
}
