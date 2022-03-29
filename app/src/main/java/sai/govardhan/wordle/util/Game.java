package sai.govardhan.wordle.util;

public class Game
{
    public Integer getCurRow() {
        return curRow;
    }
    public String getLastWord(int row)
    {
        return arr[row].toString();
    }
    public Integer getCurCol() {
        return curCol;
    }
    public String getAns(){return  ans;}
    private Integer curRow;
    private Integer curCol;
    private StringBuilder arr[];
    private String ans;
    public Game(String ans,Integer curRow,Integer curCol)
    {   this.ans=ans;
        this.curCol=curCol;
        this.curRow=curRow;
        arr=new StringBuilder[6];
        for (int i=0;i<arr.length;i++)
            arr[i]=new StringBuilder("");


    }
    public String getLetterAt(int row,int col)
    {

        return arr[row].charAt(col)+"";
    }
    public Integer[] validate()
    {
        int validatingRow=curRow-1;
        Integer verdict[]=new Integer[5];
        for(int i=0;i<5;i++)
            verdict[i]=0;
        StringBuilder tempAns=new StringBuilder(ans.toString());
        StringBuilder myAns=new StringBuilder(arr[validatingRow].toString());
        for(int i=0;i<5;i++)
        {   if(myAns.charAt(i)==tempAns.charAt(i))
                {   verdict[i]=2;
                    tempAns.setCharAt(i,'|');
                    continue;
                }


        }
        for(int i=0;i<5;i++)
        {   if(verdict[i]==2)
                continue;
            int found=tempAns.indexOf(myAns.charAt(i)+"");
            if(found!=-1)
            {   verdict[i]=1;
                tempAns.setCharAt(found,'|');
            }


        }

return verdict;
    }
    public void addLetterAt(String letter)
    {
        arr[curRow].append(letter);
        curCol+=1;
        if(curCol==5)
        {
            curCol = 0;
            curRow += 1;
        }
    }
    public void removeLetter()
    {
        curCol-=1;
        arr[curRow].deleteCharAt(curCol);
    }

}
