package com.alanworks.springbatch;

import java.io.File;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.UnexpectedJobExecutionException;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

public class FileDeletingTasklet implements Tasklet, InitializingBean {

  private Resource directory;

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    File dir = directory.getFile();
    Assert.state(dir.isDirectory());
    System.out.println(">>>>>>>>>>"+directory.getFilename()+"**"+dir.isDirectory());

    File[] files = dir.listFiles();
    System.out.println(files.length);
    for (int i = 0; i < files.length; i++) {
    	System.out.println(files[i].getName());
      boolean deleted = files[i].delete();
      System.out.println("Deleted: "+deleted);
      if (!deleted) {
        throw new UnexpectedJobExecutionException(
            "Could not delete file " + files[i].getPath());
      } else {
        System.out.println(files[i].getPath() + " is deleted!");
      }
    }
    return RepeatStatus.FINISHED;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
	  System.out.println(directory.getDescription());
    Assert.notNull(directory, "directory must be set");
  }

  public Resource getDirectory() {
    return directory;
  }

  public void setDirectory(Resource directory) {
    this.directory = directory;
  }
}
