package nl.han.ica.icss.checker;

import nl.han.ica.icss.ast.AST;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckerTest {

    @BeforeEach
    void setUp() {
    }

    private static void printErrors(AST ast) {
        ast.getErrors().forEach(System.err::println);
    }

    @Test
    void variableMustBeDefined_true(){ //CH01
        Checker sut = new Checker();
        AST ast = CheckerFixtures.VariableDefinition_true();

        sut.check(ast);

        printErrors(ast);
        assertTrue(ast.getErrors().isEmpty());
    }

    @Test
    void variableMustBeDefined_false(){ //CH01
        Checker sut = new Checker();
        AST ast = CheckerFixtures.VariableDefinition_false();

        sut.check(ast);

        printErrors(ast);
        assertFalse(ast.getErrors().isEmpty());
    }


    @Test
    void addAndSubShouldBeOfSameType_true(){ //CH02
        Checker sut = new Checker();
        AST ast = CheckerFixtures.addAndSubSameType_true();

        sut.check(ast);

        printErrors(ast);
        assertTrue(ast.getErrors().isEmpty());
    }

    @Test
    void addAndSubShouldBeOfSameType_false(){ //CH02
        Checker sut = new Checker();
        AST ast = CheckerFixtures.addAndSubSameType_false();

        sut.check(ast);

        printErrors(ast);
        assertFalse(ast.getErrors().isEmpty());
    }


    @Test
    void mulShouldUseAtLeastOneScalar_true(){ //CH02
        Checker sut = new Checker();
        AST ast = CheckerFixtures.mulShouldUseAtLeastOneScalar_true();

        sut.check(ast);

        printErrors(ast);
        assertTrue(ast.getErrors().isEmpty());
    }

    @Test
    void mulShouldUseAtLeastOneScalar_false(){ //CH02
        Checker sut = new Checker();
        AST ast = CheckerFixtures.mulShouldUseAtLeastOneScalar_false();

        sut.check(ast);

        printErrors(ast);
        assertFalse(ast.getErrors().isEmpty());
    }

    @Test
    void coloursShouldNotUsedInOperation_true(){ //CH03
        Checker sut = new Checker();
        AST ast = CheckerFixtures.coloursShouldNotUsedInOperation_true();

        sut.check(ast);

        printErrors(ast);
        assertTrue(ast.getErrors().isEmpty());
    }


    @Test
    void boolUsedInOperationShouldReturnError(){ //CH03
        Checker sut = new Checker();
        AST ast = CheckerFixtures.boolShouldNotUsedInOperation_true();

        sut.check(ast);

        printErrors(ast);
        assertFalse(ast.getErrors().isEmpty());
    }




    @Test
    void ifClauseStatementsShouldUseBoolean_true(){ //CH05 (contains variableReference)
        Checker sut = new Checker();
        AST ast = CheckerFixtures.ifClauseStatementsShouldUseBoolean_true();

        sut.check(ast);

        printErrors(ast);
        assertTrue(ast.getErrors().isEmpty());
    }

    @Test
    void ifClauseStatementsShouldUseBoolean_false(){ //CH05 (contains variableReference)
        Checker sut = new Checker();
        AST ast = CheckerFixtures.ifClauseStatementsShouldUseBoolean_false();

        sut.check(ast);

        printErrors(ast);
        assertFalse(ast.getErrors().isEmpty());
    }

    @Test
    void operationsShouldBeAbleToHaveCascadingOperations(){
        Checker sut = new Checker();
        AST ast = CheckerFixtures.operationsShouldBeAbleToHaveCascadingOperations();

        sut.check(ast);

        printErrors(ast);
        assertTrue(ast.getErrors().isEmpty());
    }
}