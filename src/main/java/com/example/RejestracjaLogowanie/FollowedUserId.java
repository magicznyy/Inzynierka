package com.example.RejestracjaLogowanie;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;


public class FollowedUserId implements Serializable {


        private Long userId;


        private Long followedUserId;

}
