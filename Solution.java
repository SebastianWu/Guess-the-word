class Solution {
    int k = 6; // each word is 6 letters long
    private int computeDist(String w1, String w2){
        int match = 0;
        for(int i=0; i<w1.length(); i++){
            if(w1.charAt(i) == w2.charAt(i)){
                match += 1;
            }
        }
        return match;
    }
    private HashMap<String, int[]> computeDistHistogram(String[] wordlist, ArrayList<String> candidates){
        HashMap<String, int[]> distHistogram = new HashMap();
        for(String word : wordlist){
            distHistogram.put(word, new int[k]); // value of histogram could be 0,1,2,3,4,5
            for(String candidate : candidates){
                int dist = computeDist(word, candidate);
                if(dist != k){distHistogram.get(word)[dist] += 1;}
            }
        }
        return distHistogram;
    }
    private int getMax(int[] arr){
        int max = 0;
        for(int i:arr){
            if(i>max){
                max = i;
            }
        }
        return max;
    }
    private String chooseMinHistHeap(HashMap<String, int[]> distHistogram){
        int minHistHeap = 100;
        String res = "";
        for(String word : distHistogram.keySet()){
            int histHeap = getMax(distHistogram.get(word));
            if(minHistHeap > histHeap){
                minHistHeap = histHeap;
                res = word;
            }
        }
        return res;
    }
    private ArrayList<String> eliminateCandidates(int match, String word, ArrayList<String> candidates){
        ArrayList<String> new_candidates = new ArrayList();
        for(String candidate : candidates){
            if(computeDist(word, candidate) == match){
                new_candidates.add(candidate);
            }
        }
        return new_candidates;
    }
    public void findSecretWord(String[] wordlist, Master master) {
        ArrayList<String> candidates = new ArrayList(Arrays.asList(wordlist));
        while(candidates.size()>1){
        // compute dist histgram of each word
            HashMap<String, int[]> distHistogram = computeDistHistogram(wordlist, candidates);
        
        // choose the one with min of histgram heap(max) to guess
            String word = chooseMinHistHeap(distHistogram);
            System.out.println(word);
            int match = master.guess(word);
            if(match == k){return;}
            
        // eliminate the one with wrong distance
            candidates = eliminateCandidates(match, word, candidates);
        }
        if(!candidates.isEmpty()){
            master.guess(candidates.get(0));
        }
        
    }
}
