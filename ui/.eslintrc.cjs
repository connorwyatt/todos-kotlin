module.exports = {
    env: {
        browser: true,
        es2020: true,
        node: true,
    },
    extends: [
        "eslint:recommended",
        "plugin:@tanstack/eslint-plugin-query/recommended",
        "plugin:@typescript-eslint/recommended",
        "plugin:prettier/recommended",
        "plugin:react/recommended",
        "plugin:react/jsx-runtime",
        "plugin:react-hooks/recommended",
        "plugin:sonarjs/recommended",
    ],
    parser: "@typescript-eslint/parser",
    parserOptions: {
        ecmaVersion: "latest",
        sourceType: "module",
    },
    plugins: [
        "@tanstack/query",
        "check-file",
        "import",
        "no-relative-import-paths",
        "prettier",
        "react",
        "react-refresh",
        "sonarjs",
    ],
    settings: {
        react: {
            version: "detect",
        },
    },
    rules: {
        "@typescript-eslint/no-empty-function": [
            "error",
            {
                allow: ["constructors"],
            },
        ],

        "@typescript-eslint/no-unused-vars": "error",

        "check-file/folder-match-with-fex": [
            "error",
            {
                "*.test.{ts,tsx}": "**/tests/",
            },
        ],

        "check-file/filename-naming-convention": ["error", { "**/*": "KEBAB_CASE" }, { ignoreMiddleExtensions: true }],

        "check-file/folder-naming-convention": [
            "error",
            {
                "**": "KEBAB_CASE",
            },
        ],

        "check-file/filename-blocklist": [
            "error",
            {
                "**/*-styles.{ts,tsx}": "*.styles.ts",
            },
        ],

        "import/order": [
            "error",
            {
                alphabetize: {
                    order: "asc",
                },

                "newlines-between": "always",
            },
        ],

        "import/newline-after-import": [
            "error",
            {
                count: 1,
                considerComments: true,
            },
        ],

        "import/no-default-export": "error",

        "no-relative-import-paths/no-relative-import-paths": [
            "error",
            {
                allowSameFolder: false,
                rootDir: "src",
                prefix: "~",
            },
        ],

        "no-restricted-imports": [
            "error",
            {
                paths: (() => {
                    const errorMessage = "Please import from styled-components."

                    return [
                        {
                            name: "@emotion/styled",
                            message: errorMessage,
                        },
                        {
                            name: "@emotion/styled-base",
                            message: errorMessage,
                        },
                        {
                            name: "styled-components/macro",
                            message: errorMessage,
                        },
                    ]
                })(),
            },
        ],

        "react/destructuring-assignment": ["error", "always"],

        "react/function-component-definition": [
            "error",
            {
                namedComponents: "arrow-function",
                unnamedComponents: "arrow-function",
            },
        ],

        "react/hook-use-state": "error",

        "react/jsx-boolean-value": ["error", "never"],

        "react/jsx-curly-brace-presence": ["error", "never"],

        "react/jsx-fragments": ["error", "syntax"],

        "react/jsx-handler-names": ["error"],

        "react/jsx-key": [
            "error",
            {
                checkFragmentShorthand: true,
                warnOnDuplicates: true,
            },
        ],

        "react/jsx-newline": "error",

        "react/jsx-no-constructed-context-values": "error",

        "react/jsx-no-useless-fragment": [
            "error",
            {
                allowExpressions: true,
            },
        ],

        "react/jsx-pascal-case": "error",

        "react/no-access-state-in-setstate": "error",

        "react/no-array-index-key": "error",

        "react/no-danger": "error",

        "react/no-namespace": "error",

        "react/no-object-type-as-default-prop": "error",

        "react/no-unstable-nested-components": "error",

        "react/no-unused-prop-types": "error",

        "react/prefer-stateless-function": "error",

        "react/prop-types": "off",

        "react/self-closing-comp": [
            "error",
            {
                component: true,
                html: true,
            },
        ],

        "react/style-prop-object": "error",

        "react-refresh/only-export-components": "error",

        "sort-imports": [
            "error",
            {
                ignoreCase: true,
                ignoreDeclarationSort: true,
            },
        ],
    },

    overrides: [
        {
            files: ["vite.config.ts"],
            rules: {
                "import/no-default-export": "off",
            },
        },
    ],
}
