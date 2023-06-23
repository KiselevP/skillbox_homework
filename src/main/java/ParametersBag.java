import java.io.File;

public class ParametersBag {

    private String pathToFolder;
    private String limitSize;

    public ParametersBag(String[] args) throws Exception {
        if (args.length == 2) {
            checkingArguments(args[0], args[1]);
            this.pathToFolder = args[0];
            this.limitSize = args[1];
        } else if (args.length == 0){
            throw new MyException("Не задан параметр пути");
        } else if (args.length == 1){
            throw new MyException("Не задан параметр лимита");
        }
    }

    public String getPath() {
        return pathToFolder;
    }

    public long getLimit(){
        return SizeCalculator.getSizeFromHumanReadable(limitSize);
    }

    private void checkingArguments(String path,
                                   String limit) throws Exception {
        String samplePath = "([a-zA-Z]:)(\\\\[a-zA-Z0-9_.-]+)+\\\\?";
        String sampleLimit = "[0-9]+[BKMGT]+b?";

        File file = new File(path);
        if (!path.matches(samplePath) && !file.exists() || !file.isDirectory()) {
            throw new MyException("Неверный формат пути или пустая папка(файл)");
        }
        if (!limit.matches(sampleLimit)) {
            throw new MyException("Неверный формат лимита");
        }
    }
}
