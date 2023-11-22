import crypto.Base58;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.bytes.MutableBytes32;

import java.util.Scanner;
import java.util.regex.Pattern;

import static crypto.Base58.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Please enter the address (hex or base58 format),or enter 'q' to quit:");
            String address = scanner.nextLine();
            if (address.equalsIgnoreCase("q")) {
                break;
            }
            try {
                if (checkAddress(address)) {
                    String hexAddress = AddressTransform.base58ToHex(address);
                    System.out.println("hexAddress:" + hexAddress);
                    System.out.println("base58Address:" + AddressTransform.hexToBase58(hexAddress));
                    System.out.println();
                } else if (Pattern.matches("[0-9a-fA-F-x]+", address) && (address.length() == 40 || address.length() == 42)) {
                    if (address.startsWith("0x")) {
                        address = address.substring(2);
                    }
                    String base58Address = AddressTransform.hexToBase58(address);
                    System.out.println("base58Address:" + base58Address);
                    System.out.println("hexAddress:" + AddressTransform.base58ToHex(base58Address));
                    System.out.println();

                } else {
                    System.out.println("The input address format is incorrect.");
                }
            } catch (Exception e) {
                System.out.println("The input address format is incorrect.");
            }
        }
    }
}

class AddressTransform {
    public static String hexToBase58(String hexAddress) {
        Bytes hash = Bytes.fromHexString(hexAddress);
        MutableBytes32 hashLow = MutableBytes32.create();
        hashLow.set(8, hash);
        return Base58.encodeChecked(hash2byte(hashLow.mutableCopy()));
    }

    public static String base58ToHex(String base58Address) {
        Bytes ret = Bytes.wrap(fromBase58(base58Address));
        return ret.toUnprefixedHexString();
    }

}
