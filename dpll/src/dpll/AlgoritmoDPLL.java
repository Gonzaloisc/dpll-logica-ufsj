package dpll;

import java.util.Random;

public class AlgoritmoDPLL {

    public StringBuffer identificarVariaveis(StringBuffer fnc) {
        StringBuffer variaveis = new StringBuffer();
        variaveis.setLength(1);
        //variaveis.setCharAt(0, '!');
        int cont = 0, ind = 0;
        for (int i = 0; i < fnc.length(); i++) {
            for (int j = 0; j < variaveis.length(); j++) {
                //TODO verificar e corrigir se preciso, as compara¬¬es de acordo com a FNC fornecida
                if ((fnc.charAt(i) == variaveis.charAt(j))
                        || (fnc.charAt(i) == '¬')
                        || (fnc.charAt(i) == '+') //TODO verificar simbolos
                        || (fnc.charAt(i) == 'Λ')
                        || (fnc.charAt(i) == '(')
                        || (fnc.charAt(i) == ')')) {
                    cont++;
                }
            }

            if (cont == 0) {
                variaveis.setCharAt(ind, fnc.charAt(i));
                ind++;
                variaveis.setLength(ind + 1);
            }
            cont = 0;
        }
        variaveis.setLength(ind);

        return variaveis;
    }

    public StringBuffer escolherVariavel(StringBuffer variaveis) {
        StringBuffer substituir = new StringBuffer();
        substituir.setLength(2);
        int aux = 0;

        //indice 0 -> variavel
        //indice 1 -> valor boleano

        Random randomVar = new Random();
        aux = randomVar.nextInt(variaveis.length());
        substituir.setCharAt(0, variaveis.charAt(aux));


        //1 = true
        //0 = false

        Random randomBin = new Random();
        aux = randomBin.nextInt(2);

        if (aux == 1) {
            substituir.setCharAt(1, '1');
        } else {
            substituir.setCharAt(1, '0');
        }
        return substituir;
    }

    public StringBuffer eliminarLiterais(StringBuffer formula) {  //formula na FNC como parametro
        StringBuffer literais = new StringBuffer();
        literais.setLength(2);
        int ind = 0;
        for (int i = 1; i < formula.length() - 1; i++) {

            //TODO verificar sinal de conjun¬ao e nega¬¬o				
            if ((formula.charAt(i - 1) == '¬') && formula.charAt(i + 1) == 'Λ') {
                literais.setLength(ind + 2);
                literais.setCharAt(ind, '¬');
                literais.setCharAt(ind + 1, formula.charAt(i));
                ind += 2;
                formula.delete(i - 1, i + 2);
            } //TODO verificar sinal de conjuncao e negacao
            else if ((formula.charAt(i) == '¬') && (formula.charAt(i - 1) == 'Λ')) {
                literais.setLength(ind + 2);
                literais.setCharAt(ind, '¬');
                literais.setCharAt(ind + 1, formula.charAt(i + 1));
                formula.delete(i - 1, i + 2);
                ind += 2;
            } //TODO verificar sinal de conjuncao
            else if ((formula.charAt(i) == 'Λ') && (formula.charAt(i + 1) == '¬')) {
                literais.setLength(ind + 1);
                literais.setCharAt(ind, formula.charAt(i + 1));
                formula.delete(i, i + 3);
                ind++;
            } //TODO verificar sinal de conjuncao
            else if ((formula.charAt(i) == 'Λ') && (formula.charAt(i + 1) == '(')
                    && (formula.charAt(i - 1) != ')')) {
                literais.setLength(ind + 1);
                literais.setCharAt(ind, formula.charAt(i - 1));
                formula.delete(i - 1, i + 1);
            } else if ((formula.charAt(i) == 'Λ') && (formula.charAt(i + 1) != '(')
                    && (formula.charAt(i - 1) == ')')) {
                literais.setLength(ind + 1);
                literais.setCharAt(ind, formula.charAt(i + 1));
                formula.delete(i, i + 2);
            }
        }
        //neste ponto, todos os literias inicialmente isolados estao excluidos e catalogados
        //TODO (a+b)Λ(¬a+c)Λb
        StringBuffer aux = new StringBuffer();
        int k = 0;
        for (int i = 0; i < literais.length(); i++) {
            for (int j = 0; j < aux.length(); j++) {
                if (literais.charAt(i) != aux.charAt(j)) {
                    aux.setCharAt(k, literais.charAt(i));
                    k++;
                }
            }
        }
        for (int i = 1; i < literais.length(); i++) {
            for (int j = 0; j < aux.length(); j++) {
                if ((literais.charAt(i) == aux.charAt(j)) && (literais.charAt(i - 1) == '¬')) {
                    formula.setCharAt(0, '0');
                    return formula;
                }
            }
        }

        for (int i = 0; i < formula.length(); i++) {
            for (int j = 0; j < literais.length(); j++) {
                if (literais.charAt(j) == '¬') {
                    if ((formula.charAt(i) == literais.charAt(j))
                            && (formula.charAt(i + 1) == literais.charAt(j + 1))) {  //TODO verificar sinal de negacao
                        int inicio = 0, fim = formula.length();
                        for (int m = i; m >= 0; m--) {
                            if (formula.charAt(m) == '(') {
                                inicio = m;
                                break;
                            }
                        }
                        for (int n = 0; n < formula.length(); n++) {
                            if (formula.charAt(n) == ')') {
                                fim = n;
                                break;
                            }
                        }
                        if (inicio > 0) {
                            formula.delete(inicio - 1, fim + 1);
                        } else if (fim < formula.length() - 1) {
                            formula.delete(inicio, fim + 2);
                        } else if ((inicio == 0) && (fim == formula.length() - 1)) {
                            formula.delete(inicio, fim + 1);
                            formula.setCharAt(0, '1');
                            return formula;
                        }

                    }
                } //TODO	 .println("~le debugada~  " + literais.length());
                else if (literais.charAt(j) == '¬') {
                    if (formula.charAt(i) == literais.charAt(j + 1)) {  //TODO verificar sinal de negacao

                        if (formula.charAt(i + 1) == '+') {  //TODO verificar sinal de disjuncao
                            formula.delete(i, i + 2);
                        } else if (formula.charAt(i - 1) == '+') {
                            formula.delete(i - 1, i + 1);
                        }
                    }
                } else if ((formula.charAt(i) == literais.charAt(j))
                        && (formula.charAt(i - 1) == '¬')) {  //TODO verificar sinal de negacao

                    if (formula.charAt(i + 1) == '+') {  //TODO verificar sinal de disjuncao
                        formula.delete(i - 1, i + 2);
                    } else if (formula.charAt(i - 2) == '+') {
                        formula.delete(i - 2, i + 1);
                    }
                } else if (formula.charAt(i) == literais.charAt(j)) {
                    int inicio = 0, fim = formula.length();
                    for (int m = i; m >= 0; m--) {
                        if (formula.charAt(m) == '(') {
                            inicio = m;
                            break;
                        }
                    }
                    for (int n = 0; n < formula.length(); n++) {
                        if (formula.charAt(n) == ')') {
                            fim = n;
                            break;
                        }
                    }
                    if (inicio > 0) {
                        formula.delete(inicio - 1, fim + 1);
                    } else if (fim < formula.length() - 1) {
                        formula.delete(inicio, fim + 2);
                    } else if ((inicio == 0) && (fim == formula.length() - 1)) {
                        formula.delete(inicio, fim + 1);
                        formula.setCharAt(0, '1');
                        return formula;
                    }

                }

            }

        }
        return formula;
    }

    public StringBuffer substituiValores(StringBuffer fnc, StringBuffer substituir) {
        StringBuffer retorno = fnc;
        char v = '1', f = '0';
        if (substituir.charAt(1) == '0') {
            v = '0';
            f = '1';
        }
        for (int i = 0; i < fnc.length() - 1; i++) {
            //TODO verificar sinal de negacao
            if ((retorno.charAt(i) == '¬') && (retorno.charAt(i + 1) == substituir.charAt(0))) {
                retorno.setCharAt(i, f);
                retorno.deleteCharAt(i + 1);
            } else if (retorno.charAt(i) == substituir.charAt(0)) {
                retorno.setCharAt(i, v);
            }
            for (int j = 0; j < retorno.length()-1; j++) {
                // .println(retorno.charAt(i));
                if ((retorno.charAt(i) == '0') && (retorno.charAt(i + 1) == '+')) {
                    retorno.delete(i, i + 2);
                } else if ((retorno.charAt(i) == '0') && (retorno.charAt(i - 1) == '+')) {
                    retorno.delete(i - 1, i + 1);
                } else if ((retorno.charAt(i) == '1')) {
                    int inicio = 0, fim = retorno.length();
                    for (int k = i; k >= 0; k--) {
                        if (retorno.charAt(k) == '(') {
                            inicio = k;
                            break;
                        }
                    }
                    for (int k = i; k < retorno.length(); k++) {
                        if (retorno.charAt(k) == ')') {
                            fim = k;
                        }
                    }
                    if (inicio > 0) {
                        retorno.delete(inicio - 1, fim + 1);
                    } else if (fim < retorno.length() - 1) {
                        retorno.delete(inicio, fim + 2);
                    } else if ((inicio == 0) && (fim == retorno.length() - 1)) {
                        retorno.delete(inicio, fim + 1);
                        retorno.setLength(1);
                        retorno.setCharAt(0, '1');
                        return retorno;
                    }


                }
            }



        }

        return retorno;
    }

    public boolean propaga(StringBuffer formula) {
        int fim = 0;
        while ((formula.charAt(0) != '1') || (formula.charAt(0) != '0')) {

            int inicio = 0;

            //Busca literais falsos sozinhos para retornar falso
            for (int i = 1; i < formula.length() - 1; i++) {
                if (((formula.charAt(i - 1) == '0') && (formula.charAt(i) == 'Λ'))
                        || ((formula.charAt(i + 1) == '0') && (formula.charAt(i) == 'Λ'))) {
                    return false;
                }

            }

            for (int i = fim; i < formula.length(); i++) {
                if (formula.charAt(i) == '(') {
                    inicio = i;
                } else if (formula.charAt(i) == ')') {
                    fim = i;
                    break;
                }

            }
            for (int i = inicio; i < fim; i++) {
                if (formula.charAt(i) == '1') {  //eliminando a disjuncao caso encontre um V
                    if (inicio > 0) {  //verificar se ¬ o primeiro indice
                        if (formula.charAt(inicio - 1) == 'Λ') {
                            formula.delete(inicio - 1, fim);
                        }
                    } else if (fim < formula.length() - 1) {  //verifica se ¬ o ultimo indice
                        if (formula.charAt(fim + 1) == 'Λ') {
                            formula.delete(inicio, fim + 1);
                        }
                    } else if ((inicio == 0) && (fim == formula.length() - 1)) {
                        formula.delete(inicio, fim);
                    }
                    if (formula.length() == 0) {
                        return true;
                    }
                } else if (formula.charAt(i) == '0') {  //eliminando F da disjuncao 
                    if (formula.charAt(i + 1) == '+') {
                        formula.delete(i, i + 1);
                        fim -= 2;  //Ajustando o indice fim
                    } else if (formula.charAt(i - 1) == '+') {
                        formula.delete(i - 1, i);
                        fim -= 2;  //Ajustando o indice fim
                    }
                }
                if (fim - inicio == 2) {
                    
                    formula.deleteCharAt(inicio);
                    
                    formula.deleteCharAt(fim-1);
                    return true;
                }
            }
        }
        return true;
    }
}
