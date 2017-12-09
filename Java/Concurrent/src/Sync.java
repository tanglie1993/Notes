/**
 * Created by pc on 2017/12/6.
 */
public class Sync {

    static class Person{
        boolean isMale = true;
        boolean isFemale = false;

        synchronized void switchGender(){
            isMale = !isMale;
            isFemale = !isFemale;
        }

        synchronized boolean checkGender(){
            return isFemale != isMale;
        }
    }

    static class SwitchThread extends Thread{

        Person person;

        SwitchThread(Person person){
            this.person = person;
        }

        @Override
        public void run(){
            for(int i = 0; i < 1000; i++){
                   person.switchGender();
                   if(!person.checkGender()){
                       System.out.println("person.isFemale == person.isMale");
                   }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Person person = new Person();
        for(int i = 0; i < 5; i++)
            new SwitchThread(person).start();
    }
}
