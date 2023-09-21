module.exports = {
    plugins: ["stylelint-order"],
    extends: ["stylelint-config-standard"],
    customSyntax: "postcss-styled-syntax",
    rules: {
        "color-hex-length": "long",
        "color-named": "never",
        "declaration-no-important": true,
        "declaration-property-value-no-unknown": true,
        "font-weight-notation": "numeric",
        "length-zero-no-unit": true,
        "media-feature-name-unit-allowed-list": {
            "/height&/": ["rem"],
            "/width&/": ["rem"],
        },
        "order/order": ["custom-properties", "declarations", "rules", "at-rules"],
        "order/properties-alphabetical-order": true,
    },
}
