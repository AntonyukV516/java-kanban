package Model;

import java.util.Objects;

 abstract class PreTask {
    String name;
    String description;
    Status status;

    public PreTask(String description, Status status, String name) {
        this.description = description;
        this.status = status;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


     @Override
     public boolean equals(Object o) {
         if (this == o) return true;
         if (o == null || getClass() != o.getClass()) return false;
         PreTask preTask = (PreTask) o;
         return Objects.equals(getDescription(), preTask.getDescription());
     }

     @Override
     public int hashCode() {
         return Objects.hashCode(getDescription());
     }

     @Override
     public String toString() {
         return "PreTask{" +
                 "description='" + description + '\'' +
                 ", name='" + name + '\'' +
                 ", status=" + status +
                 '}';
     }
 }
