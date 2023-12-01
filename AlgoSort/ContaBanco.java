class ContaBanco implements Comparable<ContaBanco> {
    int agencia;
    int numero;
    double balanco;
    long cpf;

    public ContaBanco(int agencia, int numero, double balanco, long cpf) {
        this.agencia = agencia;
        this.numero = numero;
        this.balanco = balanco;
        this.cpf = cpf;
    }

    @Override
    public int compareTo(ContaBanco outro) {
        int comparaCPF = Long.compare(this.cpf, outro.cpf);
        if (comparaCPF != 0) {
            return comparaCPF;
        }

        if (this.agencia != outro.agencia) {
            return Integer.compare(this.agencia, outro.agencia);
        }
        return Integer.compare(this.numero, outro.numero);
    }

    @Override
    public String toString() {
        return String.format("%d;%d;%.2f;%d", agencia, numero, balanco, cpf);
    }
}
