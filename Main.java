import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

interface Pagamento {
}

class Locacao {
    protected String modeloCarro;
    protected LocalDateTime inicio;
    protected LocalDateTime fim;
    protected double valorPorHora;
    protected double valorDiario;

    public Locacao(String modeloCarro, LocalDateTime inicio, LocalDateTime fim, double valorPorHora, double valorDiario) {
        this.modeloCarro = modeloCarro;
        this.inicio = inicio;
        this.fim = fim;
        this.valorPorHora = valorPorHora;
        this.valorDiario = valorDiario;
    }

    public long calcularDuracaoEmHoras() {
        return Duration.between(inicio, fim).toHours();
    }
}

class LocacaoCarro extends Locacao implements Pagamento {
    public LocacaoCarro(String modeloCarro, LocalDateTime inicio, LocalDateTime fim, double valorPorHora, double valorDiario) {
        super(modeloCarro, inicio, fim, valorPorHora, valorDiario);
    }

    private double calcularValorLocacao() {
        long duracaoEmHoras = calcularDuracaoEmHoras();
        return (duracaoEmHoras <= 12) ? duracaoEmHoras * valorPorHora : ((double) duracaoEmHoras / 24 + 1) * valorDiario;
    }

    private double calcularImposto() {
        double valorLocacao = calcularValorLocacao();
        return (valorLocacao <= 100.00) ? valorLocacao * 0.20 : valorLocacao * 0.15;
    }

    private double calcularTotalPagamento() {
        return calcularValorLocacao() + calcularImposto();
    }

    public void imprimirNota() {
        System.out.println("Modelo do carro: " + modeloCarro);
        System.out.println("Valor da locação: R$ " + String.format("%.2f", calcularValorLocacao()));
        System.out.println("Valor do imposto: R$ " + String.format("%.2f", calcularImposto()));
        System.out.println("Valor total do pagamento: R$ " + String.format("%.2f", calcularTotalPagamento()));
    }
}

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> carros = new ArrayList<>();
        carros.add("Toyota");
        carros.add("Hilux");
        carros.add("Onix");
        carros.add("Corolla");

        System.out.println("Carros disponíveis:");
        for (int i = 0; i < carros.size(); i++) {
            System.out.println((i + 1) + ". " + carros.get(i));
        }

        System.out.print("Escolha o carro (1-4): ");
        int escolha = scanner.nextInt();
        scanner.nextLine();

        String modeloCarro = carros.get(escolha - 1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        System.out.print("Informe o instante inicial (dd/MM/yyyy HH:mm): ");
        LocalDateTime inicio = LocalDateTime.parse(scanner.nextLine(), formatter);

        System.out.print("Informe o instante final (dd/MM/yyyy HH:mm): ");
        LocalDateTime fim = LocalDateTime.parse(scanner.nextLine(), formatter);

        System.out.print("Informe o valor por hora: ");
        double valorPorHora = scanner.nextDouble();

        System.out.print("Informe o valor diário: ");
        double valorDiario = scanner.nextDouble();

        LocacaoCarro locacao = new LocacaoCarro(modeloCarro, inicio, fim, valorPorHora, valorDiario);
        locacao.imprimirNota();

        scanner.close();
    }
}
