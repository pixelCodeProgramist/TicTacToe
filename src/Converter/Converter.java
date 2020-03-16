package Converter;

public class Converter {
    public int convertStringNumberToInt(String number){
        int numberOfBlocks=-1;

        try {
            numberOfBlocks = Integer.valueOf(number);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return numberOfBlocks;
    }

    public int[] convertIdToInt(String id){
        String [] splitedId;
        splitedId = id.split("|");

        int [] splitedIdNumber = new int[2];
        splitedIdNumber[0] = Integer.valueOf(splitedId[2]);
        splitedIdNumber[1] = Integer.valueOf(splitedId[0]);

        return splitedIdNumber;
    }
}
