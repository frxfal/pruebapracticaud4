package org.iesvdm.reservamesa;


public class ReservaMesa {

    private int tamanioMesa;
    private int[] array;

    public int[] getMesas() {
        return array;
    }

    public void setMesas(int[] mesas) {
        this.array = mesas;
    }

    public int getTamanioMesa() {
        return tamanioMesa;
    }

    public void setTamanioMesa(int tamanioMesa) {
        this.tamanioMesa = tamanioMesa;
    }

    void rellenarMesas()
    {
        for (int i = 0; i < array.length; i++)
        {
            int aleatorio = (int) (Math.random() * tamanioMesa+1);

            array[i]= aleatorio;
        }
    }

    void imprimir()
    {
        String ocupacion = "";
        String cabecera1 = "";
        String cabecera2 = "";
        String cabecera3 = "";
        String cabecera4 = "";

        for (int i = 0; i < array.length; i++)
        {
            cabecera1 = cabecera1 + "────┬";
            cabecera2 = cabecera2 + " "+  (i+1) + (i+1<10 ? " ": "")+  " |";
            cabecera3 = cabecera3 + "────┼";
            cabecera4 = cabecera4 + "────┴";
            ocupacion = ocupacion + " " + array[i] + "  |" ;
        }

        System.out.println("\n");
        System.out.println("┌─────────┬" + cabecera1);
        System.out.println("│Mesa nº  │" + cabecera2);
        System.out.println("├─────────┼" +cabecera3);
        System.out.println("│Ocupación│" + ocupacion);
        System.out.println("└─────────┴" +cabecera4);
        System.out.println("\n");
    }


    int buscarPrimeraMesaVacia()
    {
        boolean noEncontrada = true;
        int mesaVacia = -1;

        for (int i = 0; i < array.length; i++)
        {
            if (array[i] == 0 && noEncontrada)
            {
                mesaVacia = i;
                noEncontrada = false;
            }
        }

        return mesaVacia;
    }

    int buscarMesaParaCompartir(int numPersonas)
    {
        boolean noEncontrada = true;
        int mesaParaCompartir = -1;

        for (int i = 0; i < array.length; i++)
        {
            if (array[i] + numPersonas <= tamanioMesa && noEncontrada)
            {
                mesaParaCompartir = i;
                noEncontrada = false;
            }
        }

        return mesaParaCompartir;
    }


    int buscarMesaCompartirMasCercaDe(int mesaBuscada, int numPersonas)
    {
        boolean noEncontrada = true;
        int mesaSalida = -1;

        int iDer = mesaBuscada;
        int iIzq = mesaBuscada;

        while ( noEncontrada && (iIzq >= 0 || iDer < array.length) )
        {

            if (iIzq >= 0 && array[iIzq] + numPersonas <= tamanioMesa )
            {
                mesaSalida = iIzq;
                noEncontrada = false;
            }
            iIzq--;

            if (noEncontrada && iDer < array.length && array[iDer] + numPersonas <= tamanioMesa) {
                mesaSalida = iDer;
                noEncontrada = false;
            }
            iDer++;
        }

        return mesaSalida;
    }

    int buscarMesaCompartirMasAlejadaDe(int mesaBuscada, int numPersonas)
    {
        boolean noEncontrada = true;
        int mesaSalida = -1;

        int iDer = array.length-1;
        int iIzq = 0;

        while ( noEncontrada && (iIzq <= mesaBuscada || iDer >= mesaBuscada) )
        {
            if (iIzq <= mesaBuscada && array[iIzq] + numPersonas <= tamanioMesa )
            {
                mesaSalida = iIzq;
                noEncontrada = false;
            }
            iIzq++;

            if (noEncontrada && iDer >= mesaBuscada && array[iDer] + numPersonas <= tamanioMesa) {
                mesaSalida = iDer;
                noEncontrada = false;
            }
            iDer++;
        }

        return mesaSalida;
    }

    int buscarCompartirNMesasConsecutivas(int numMesasConsecutivas,int numPersonas) {

        boolean noEncontrada = true;
        int mesaSalida = -1;

        for (int i = 0; noEncontrada && i < array.length-numMesasConsecutivas; i++) {

            int totalMesasConsecutivas = 0;
            for (int j = 0; j < numMesasConsecutivas; j++) {
                totalMesasConsecutivas += array[i+j];
            }

            if (totalMesasConsecutivas + numPersonas <= numMesasConsecutivas*tamanioMesa && noEncontrada)
            {
                mesaSalida = i;
                noEncontrada = false;
            }

        }

        return mesaSalida;
    }

    int comensalesTotales()
    {
        int comensalesTotales = 0;

        for (int i = 0; i < array.length; i++)
        {
            comensalesTotales = comensalesTotales + array[i];
        }
        return comensalesTotales;
    }

}

