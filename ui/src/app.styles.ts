import styled from "styled-components"

export const Container = styled.div`
    align-items: center;
    display: grid;
    grid-template: ". . ." 1fr ". todosList ." auto ". . ." 1fr / 1fr auto 1fr;
    height: 100vh;
    justify-content: center;
`

export const TodosListContainer = styled.div`
    grid-area: todosList;
    width: 48rem;
`
