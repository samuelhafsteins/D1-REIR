
public class Example{
    private int private_variable = 1;
    public int public_variable = 2;

    public int public_function(){
        private_variable -= 1;
        return private_variable;
    }

    private int private_function(){
        private_variable += 1;
        return private_variable;
    }
}