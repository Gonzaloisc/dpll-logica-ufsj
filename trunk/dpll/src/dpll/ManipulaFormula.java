/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dpll;

import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Rodrigo
 */
public class ManipulaFormula {

    final static char E = 'Λ', OU = '+';

    public static void main(String args[]) {
        System.out.println(separaFormula("(B↔D)"));
    }

    public static ArrayList<String> separaFormula(String formula) {
        ArrayList<String> partes = new ArrayList<String>();
        int parentese = 0;
        String parte = "", parteAux = "";
        boolean modificou = false;
        formula = corrigeParenteses(formula);
        char[] aux = formula.toCharArray();
        for (char c : aux) {
            if (c == '(') {
                parentese++;
            }
            if (c == ')') {
                parentese--;
            }
            if ((c == E || c == OU) && parentese == 0) {
                parteAux = removeImplicacao(parte);
                if (!parte.equals(parteAux)) {
                    modificou = true;
                }
                parteAux = verificaNegacao(parteAux);
                partes.add(parteAux);
                partes.add(c + "");
                parte = "";
            } else {
                parte += c;
            }
        }
        parteAux = removeImplicacao(parte);
        if (!parte.equals(parteAux)) {
            modificou = true;
        }
        parteAux = verificaNegacao(parteAux);
        partes.add(parteAux);
        if (modificou) {
            return separaFormula(juntarFormula(partes));
        }

        return partes;
    }

    public static String removeImplicacao(String formula) {
        String aux[], aux2 = "", aux3 = "";

        if (formula.contains("→")) {
            aux = formula.split("→");
            for (int i = 0; i < aux.length - 1; i++) {
                aux[i] = "¬" + aux[i];
            }

            return "(" + StringUtils.join(aux, "+") + ")";

        } else if (formula.contains("↔")) {
            formula = formula.replace("(", "");
            formula = formula.replace(")", "");
            aux = formula.split("↔");
            for (int i = 0; i < aux.length - 1; i++) {
                if (i == 0) {
                    aux2 = removeImplicacao(aux[i] + "→" + aux[i + 1]);
                    aux2 += E;
                    aux2 += removeImplicacao(aux[i + 1] + "→" + aux[i]);
                } else {
                    aux3 = removeImplicacao(aux2 + "→" + aux[i + 1]);
                    aux3 += E;
                    aux3 += removeImplicacao(aux[i + 1] + "→" + aux2);
                    aux2 = aux3;
                }

            }
            return aux2;
        }
        return formula;

    }

    public static String corrigeParenteses(String formula) {
        char aux;
        int nivelParenteseAberto = 0;
        aux = formula.charAt(0);
        if (aux == '(') {
            for (int i = 0; i < formula.length(); i++) {
                aux = formula.charAt(i);
                if (aux == '(') {
                    nivelParenteseAberto++;
                } else if (aux == ')') {
                    nivelParenteseAberto--;
                    if (nivelParenteseAberto == 0 && i == formula.length() - 1) {
                        formula = formula.substring(1, i);
                        break;
                    } else if (nivelParenteseAberto == 0) {
                        break;
                    }
                }
            }
        }
        return formula;
    }

    public static String verificaNegacao(String formula) {
        int indice = -1, indiceInicial = 0;
        String formulaFinal = formula;
        do {
            indice++;
            char[] vFormula = formulaFinal.toCharArray();
            indice = formulaFinal.indexOf("¬", indice);
            if (indice == -1) {
                break;
            }
            if (vFormula[indice + 1] == '(') {
                indiceInicial = indice;
                int nivelParentese = 1, aux = indice + 1;
                String novaFormula = "(";
                do {
                    aux++;
                    if (nivelParentese == 1) {
                        if (Dpll.literais.contains(vFormula[aux] + "")) {
                            if (vFormula[aux - 1] == '¬') {
                                novaFormula += vFormula[aux];
                            } else {
                                novaFormula += "¬" + vFormula[aux];
                            }
                        } else if (vFormula[aux] == E) {
                            novaFormula += OU;
                        } else if (vFormula[aux] == OU) {
                            novaFormula += E;
                        }
                    }
                    if (vFormula[aux] == '(') {
                        nivelParentese++;
                        novaFormula += "¬";
                    } else if (vFormula[aux] == ')') {
                        nivelParentese--;
                    }
                    if (nivelParentese > 1 || vFormula[aux] == ')') {
                        novaFormula += vFormula[aux];
                    }

                } while (nivelParentese > 0);
                formulaFinal = formulaFinal.substring(0, indice) + novaFormula + formulaFinal.substring(aux + 1, formulaFinal.length());
            } else if (formulaFinal.charAt(indice + 1) == '¬') {
                formulaFinal = formulaFinal.substring(0, indice) + formulaFinal.substring(indice + 2, formula.length());
            }
        } while (indice > -1);
        return formulaFinal;
    }

    public static String juntarFormula(ArrayList<String> formula) {
        String formula2 = "";
        for (String f : formula) {
            if (!Dpll.operacoes.contains(f)) {
                formula2 += f;
            } else {
                formula2 += f;
            }
        }
        return formula2;
    }
}