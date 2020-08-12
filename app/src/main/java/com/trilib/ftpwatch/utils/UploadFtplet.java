package com.trilib.ftpwatch.utils;

import android.os.Message;

import com.trilib.ftpwatch.services.FtpService;

import org.apache.ftpserver.ftplet.DefaultFtplet;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.ftplet.FtpRequest;
import org.apache.ftpserver.ftplet.FtpSession;
import org.apache.ftpserver.ftplet.FtpletResult;

import java.io.IOException;

public class UploadFtplet extends DefaultFtplet {
    @Override
    public FtpletResult beforeCommand(FtpSession session, FtpRequest request) throws FtpException, IOException {
        return super.beforeCommand(session, request);
    }

    @Override
    public FtpletResult onUploadEnd(FtpSession session, FtpRequest request)
            throws FtpException, IOException {

        String filename = request.getArgument();
        Message msg=new Message();
        msg.what=FtpService.MESSAGE_FTP_UPLOAD_END;
        msg.obj=filename;
        FtpService.sendMessage(msg);
        return super.onUploadEnd(session, request);
    }
}
