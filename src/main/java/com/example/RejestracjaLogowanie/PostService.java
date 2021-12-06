package com.example.RejestracjaLogowanie;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PostService {

    public static List<String> extractTags(Post post) {
        Pattern pattern = Pattern.compile("#(\\S+)");
        Matcher matcher = pattern.matcher(post.getTags());
        List<String> tags = new ArrayList<String>();
        while (matcher.find()) {
            tags.add(matcher.group(1));
        }
        return tags;
    }

    public static boolean matchTag(String value, Post post){
        List<String> tags = extractTags(post);
        for (String tag:tags) {
            if(tag.equals(value))
                return true;
        }
        return false;
    }

    public static List<Post> postsMatchTags(String value, List<Post> posts){

        List<Post> matchingPosts = new ArrayList<>();
        for (Post post: posts) {
            if(matchTag(value,post)) {
                matchingPosts.add(post);
            }
        }

        return matchingPosts;
    }


}
