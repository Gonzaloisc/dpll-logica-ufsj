/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dpll;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.java.dev.eval.Expression;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Rodrigo
 */
public class TabelaVerdade {

    private char[] literais;
    private boolean[][] tabela;

    public TabelaVerdade(String formula) {
        int linhas, colunas;
        ArrayList<String> partes;
        String l = "";

        char[] formula2 = formula.toCharArray();
        for (char c : formula2) {
            if (Dpll.literais.contains(c + "") && !l.contains(c + "")) {
                l += c;
            }
        }
        this.literais = l.toCharArray();

        linhas = (int) Math.pow(2, literais.length);
        colunas = literais.length;
        tabela = new boolean[linhas][colunas + 1];
        String bin = "";
        for (int i = linhas - 1; i >= 0; i--) {
            bin = StringUtils.leftPad(Integer.toBinaryString(i), colunas);

            for (int j = 0; j < colunas; j++) {
                if (bin.charAt(j) == '1') {
                    tabela[(linhas - 1) - i][j] = true;
                }
            }
        }

        partes = ManipulaFormula.separaFormula(formula);

        String f = ManipulaFormula.juntarFormula(partes);
        String aux = "";

        System.out.println(f);
        f = f.replace("Λ", " && ");
        f = f.replace("+", " || ");
        f = f.replace("(", "");
        f = f.replace(")", "");
        for (char x : literais) {
            aux = f.replace("¬" + x, x + "!=xy");
            if (aux.equals(f)) {
                f = f.replace(x + "", x + "==xy");
            } else {
                f = aux;
            }
        }
        Expression exp = new Expression(f);
        BigDecimal c;

        Map<String, BigDecimal> variables = new HashMap<String, BigDecimal>();

        variables.put("xy", new BigDecimal("1"));

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                if (tabela[i][j]) {
                    variables.put(literais[j] + "", new BigDecimal("1"));
                } else {
                    variables.put(literais[j] + "", new BigDecimal("0"));
                }

            }

            c = exp.eval(variables);
            if (c.intValue() == 1) {
                tabela[i][colunas] = true;
            }
        }

        for (int i = 0; i < tabela.length; i++) {
            for (int j = 0; j <= literais.length; j++) {
                if (tabela[i][j]) {
                    System.out.print("| V ");
                } else {
                    System.out.print("| F ");
                }
            }
            System.out.print("|\n");
        }
        System.out.println(obterFNC());
    }

    public String obterFNC() {
        String formula = "";
        int cont = 0;//contador de quantos F a tabela possui.
        for (int i = 0; i < tabela.length; i++) {
            if (!tabela[i][literais.length]) {
                cont++;
                formula += "(";
                for (int j = 0; j < literais.length; j++) {
                    if (tabela[i][j]) {
                        formula += "¬" + literais[j];
                    } else {
                        formula += literais[j];
                    }
                    if (j < literais.length - 1) {
                        formula += "+";
                    }
                }
                formula += ")Λ";
            }
        }
        if(cont==tabela.length){
            return "┴";
        }else if (cont == 0){
            return "┬";
        }
        formula = formula.substring(0, formula.length() - 1);
        return formula;
    }
}
