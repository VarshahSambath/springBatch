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

//  private Resource directory;
  
  private  String filePath;

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    	File fileToDelete =new File(filePath);
    	boolean deleted = fileToDelete.delete();
      if (!deleted) {
        throw new UnexpectedJobExecutionException(
            "Could not delete file " + fileToDelete.getPath());
      } else {
        System.out.println(fileToDelete.getPath() + " is deleted!");
      }
    return RepeatStatus.FINISHED;
  }

	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath){
		this.filePath=filePath;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(filePath, "filePath must be set");
	}


}
