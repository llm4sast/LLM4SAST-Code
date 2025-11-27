@RestController
@RequestMapping("/example")
class ExampleController {
  private final OpenAIClient client;

  @PostMapping("/example")
  public ResponseEntity<?> example(@RequestBody Map<String, String> payload) {
    String promptText = payload.get("prompt_text");
    String systemText = payload.get("sys_text");
    String developerText = payload.get("dev_text");
    ChatCompletionCreateParams request =
        ChatCompletionCreateParams.builder()
            .model(ChatModel.GPT_3_5_TURBO)
            .maxCompletionTokens(2048)
            .addSystemMessage(systemText)
            .addDeveloperMessage(developerText)
            .addUserMessage(promptText)
            .build();
    var completion = client.chat().completions().create(request);
    return ResponseEntity.ok(
        Map.of(
            "response",
            completion.choices().stream()
                .flatMap(choice -> choice.message().content().stream())
                .collect(Collectors.joining(" | "))));
  }
}
