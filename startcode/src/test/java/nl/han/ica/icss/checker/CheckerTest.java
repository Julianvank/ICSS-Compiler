package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.AST;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void check() {
    }

    @Test
    void VariableMustBeDefined_true(){
        Checker sut = new Checker();
        AST ast = CheckerFixtures.VariableDefinition_true();

        sut.check(ast);

        assertTrue(ast.getErrors().isEmpty());
    }

    @Test
    void VariableMustBeDefined_false(){
        Checker sut = new Checker();
        AST ast = CheckerFixtures.VariableDefinition_false();

        sut.check(ast);

        assertFalse(ast.getErrors().isEmpty());
    }
}