import java.util.ArrayList;

public class PlaylistNodeSecondaryIndex extends PlaylistNode {

	private ArrayList<Integer> audioIds;
	private ArrayList<String> genres;
	private ArrayList<PlaylistNode> children;

	public PlaylistNodeSecondaryIndex(PlaylistNode parent) {
		super(parent);
		audioIds = new ArrayList<Integer>();
		genres = new ArrayList<String>();
		children = new ArrayList<PlaylistNode>();
		this.type = PlaylistNodeType.Internal;
	}
	
	public PlaylistNodeSecondaryIndex(PlaylistNode parent, ArrayList<String> genres, ArrayList<PlaylistNode> children, ArrayList<Integer> audioIds) {
		super(parent);
		this.audioIds = audioIds;
		this.genres = genres;
		this.children = children;
		this.type = PlaylistNodeType.Internal;
	}
	
	// GUI Methods - Do not modify
	public ArrayList<PlaylistNode> getAllChildren()
	{
		return this.children;
	}
	
	public PlaylistNode getChildrenAt(Integer index) {
		
		return this.children.get(index);
	}
	

	public Integer genreCount()
	{
		return this.genres.size();
	}
	
	public String genreAtIndex(Integer index) {
		if(index >= this.genreCount() || index < 0) {
			return "Not Valid Index!!!";
		}
		else {
			return this.genres.get(index);
		}
	}
	
	
	// Extra functions if needed

	public Integer audioIdCount(){
		return this.audioIds.size();
	}

	public Integer audioIdAtIndex(Integer index){

		if(index >= this.audioIdCount() || index < 0){
			return -1;
		}
		else{
			return this.audioIds.get(index);
		}
	}

	public ArrayList<Integer> getAllAudioIds(){
		return this.audioIds;
	}

	public ArrayList<String> getAllGenres(){
		return this.genres;
	}

	public void addAudioId(Integer index, Integer audioId){
		audioIds.add(index,audioId);
	}

	public void addAudioId(Integer audioId){
		audioIds.add(audioId);
	}

	public void addChild(Integer index, PlaylistNode child){
		children.add(index,child);
	}

	public void addChild(PlaylistNode child)
	{
		children.add(child);
	}

	public void addGenre(Integer index, String genre){
		genres.add(index, genre);
	}

	public void addGenre(String genre)
	{
		genres.add(genre);
	}

	public void setAudioId(Integer index, Integer audioId)
	{
		audioIds.set(index, audioId);
	}

	public void setChild(Integer index, PlaylistNode child)
	{
		children.set(index, child);
	}

	public void setGenre(Integer index, String genre)
	{
		genres.set(index, genre);
	}

}
