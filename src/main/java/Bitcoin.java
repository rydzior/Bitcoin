import org.apache.commons.codec.digest.DigestUtils;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
class Transaction{
    public double amount;
    public String previousHash, lastTransaction;
    public int nounce;
    public Transaction(double amount, String previousHash, int nounce){
        this.amount = amount;
        this.previousHash = previousHash;
        this.nounce = nounce;
    }
    public String toString(){
        return this.amount + " " + this.previousHash + " " + this.nounce;
    }
}
public class Bitcoin {
    public static void main(String[] args) {
        Random rand = new Random();
        List<Transaction> transactions = new ArrayList<Transaction>();
        byte[] bytes = new byte[32];
        rand.nextBytes(bytes);
        String randString = Base64.getEncoder().encodeToString(bytes);
        for (int i = 0; i < 5; i++) {
            double liczba = 1 + (rand.nextDouble() * (200 - 1));
            String previousHash;
            previousHash = (transactions.isEmpty()) ? randString : transactions.get(i - 1).lastTransaction;
            Transaction transaction = new Transaction(liczba, previousHash, 0);
            String currentHash = "";
            while(!currentHash.endsWith("00000")){
                transaction.nounce++;
                currentHash = DigestUtils.md5Hex(transaction.toString());
            }
            transaction.lastTransaction = currentHash;
            transactions.add(transaction);
        }
        for (int i = 1; i < transactions.size(); i++) {
            System.out.println(transactions.get(i).toString());
        }
    }
}

