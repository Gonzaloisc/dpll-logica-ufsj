package dpll;




public class DPLLMain {

    /**
     * @param args
     */
    // TODO Auto-generated method stub
    public boolean DPLLMain(StringBuffer fnc) {



        AlgoritmoDPLL dpll = new AlgoritmoDPLL();

        StringBuffer variaveis = new StringBuffer();
        StringBuffer substituir = new StringBuffer();

        variaveis = dpll.identificarVariaveis(fnc);

        fnc = dpll.eliminarLiterais(fnc);

        variaveis = dpll.identificarVariaveis(fnc);

        substituir = dpll.escolherVariavel(variaveis);

        fnc = dpll.substituiValores(fnc, substituir);

        boolean resp = dpll.propaga(fnc);

        if (resp) {
            System.out.println("Sucesso!");
            return true;
        } else {
            System.out.println("Fracasso!");
            return false;
        }
    }
}
