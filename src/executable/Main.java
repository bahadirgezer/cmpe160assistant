package executable;


public class Main {
    public static void main(String[] args) {
        BufferedReader input = new BufferedReader(new FileReader(new File(args[0])));
        Pattern passengerPattern = Pattern.compile("(\\d+)");
        Pattern cargoPattern = Pattern.compile("([a-e]+) (\\d+)");
        Matcher passengerMatcher, cargoMatcher;
        String currLine;
    
        while((currLine = input.readLine()) != null) {
            passengerMatcher = passengerPattern.matcher(currLine);
            cargoMatcher = cargoPattern.matcher(currLine);
            if(passengerMatcher.matches()) {
        
            } else if(cargoMatcher.matches()) {
        
            }
        }
    }
}